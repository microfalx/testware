package net.tarau.testware.reporting.template;

import net.tarau.binserde.utils.ArgumentUtils;
import net.tarau.testware.reporting.ReportingException;
import net.tarau.testware.reporting.utils.StringUtils;
import net.tarau.testware.reporting.utils.TimeUtils;
import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.log.LogChute;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generates the site.
 * <p>
 * The class works with a {@link  net.tarau.testware.core.repository.Repository} and renders the site using the latest session.
 * The site is generated in such a way to preserve previously generated sessions and provides aggregates statistics but also
 * links to the previous sessions.
 */
public class Template {

    private static final Logger LOGGER = Logger.getLogger(Template.class.getName());

    private final Page page;
    private final net.tarau.resource.Resource resource;
    private final String template;

    private VelocityEngine engine;
    private VelocityContext context;

    private final Collection<String> globals = new ArrayList<>();

    public static Template create(Page page, net.tarau.resource.Resource resource, String template) {
        return new Template(page, resource, template);
    }

    private Template(Page page, net.tarau.resource.Resource resource, String template) {
        ArgumentUtils.requireNonNull(page);
        ArgumentUtils.requireNonNull(template);
        ArgumentUtils.requireNonNull(resource);

        this.page = page;
        this.template = template;
        this.resource = resource;
    }

    /**
     * Returns the page to be rendered.
     *
     * @return a non-null instance
     */
    public Page getPage() {
        return page;
    }

    /**
     * Returns the resource where the rendered template will be written.
     *
     * @return a non-null instance
     */
    public net.tarau.resource.Resource getResource() {
        return resource;
    }

    /**
     * Returns the template used to render the site.
     *
     * @return a non-null instance
     */
    public String getTemplate() {
        return template;
    }

    /**
     * Registers a new variable in the template context.
     *
     * @param name  the variable name
     * @param value the variable value
     * @return self
     */
    public Template withVariable(String name, Object value) {
        initEngine();
        context.put(name, value);

        return this;
    }

    /**
     * Registers a global template.
     *
     * @param global the template file name
     */
    public void registerGlobal(String global) {
        globals.add(global);
    }

    /**
     * Renders the template.
     *
     * @throws IOException if an I/O exception occurs
     */
    public void execute() throws IOException {
        initEngine();

        StringWriter sw = new StringWriter();
        merge(sw, template);

        context.put("body", sw.toString());
        merge(resource.getWriter(), "layout/default_layout.vm");
    }

    private void merge(Writer writer, String template) throws IOException {
        try {
            engine.mergeTemplate(template, "UTF-8", context, writer);
        } catch (ResourceNotFoundException e) {
            throw new IOException("Failed to resolve template " + template, e);
        } catch (Exception e) {
            throw new IOException("Cannot apply template " + template, e);
        }
    }

    private void initEngine() {
        if (context != null) {
            return;
        }
        context = new VelocityContext();
        setupContext();
        setupGlobals();

        engine = new VelocityEngine();
        ExtendedProperties eprops = new ExtendedProperties();

        LOGGER.fine("Initialize Velocity Engine");

        eprops.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.LOGGER.Log4JLogChute");
        eprops.setProperty(RuntimeConstants.RUNTIME_LOG, "");

        eprops.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        eprops.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, new VelocityLogger());

        eprops.setProperty(RuntimeConstants.VM_MAX_DEPTH, 20);
        eprops.setProperty(RuntimeConstants.PARSER_POOL_SIZE, 5);

        eprops.setProperty(RuntimeConstants.VM_LIBRARY_AUTORELOAD, "false");
        eprops.setProperty(RuntimeConstants.VM_PERM_ALLOW_INLINE_REPLACE_GLOBAL, Boolean.TRUE);

        eprops.setProperty(RuntimeConstants.VM_PERM_INLINE_LOCAL, false);

        eprops.setProperty("classpath.resource.loader.class", ClassPathResourceLoader.class.getName());
        eprops.setProperty(RuntimeConstants.VM_LIBRARY, getGlobals());

        engine.setExtendedProperties(eprops);

        try {
            engine.init();
        } catch (Exception e) {
            throw new ReportingException("Cannot initialize Velocity Engine", e);
        }
    }

    private String getGlobals() {
        StringBuilder builder = new StringBuilder();
        Iterator<String> iterator = globals.iterator();
        while (iterator.hasNext()) {
            String global = iterator.next();
            builder.append("global/").append(global);
            if (iterator.hasNext()) {
                builder.append(",");
            }
        }
        return builder.toString();
    }

    private void setupGlobals() {
        registerGlobal("site.vm");
        registerGlobal("navigation.vm");
        registerGlobal("card.vm");
        registerGlobal("chart.vm");
        registerGlobal("script.vm");
        registerGlobal("icon.vm");
        registerGlobal("modal.vm");
    }

    private void setupContext() {
        context.put("page", page);
        context.put("site", page.getSite());
        context.put("navigation", page.getSite().getNavigation());
        context.put("dom", DomGenerator.class);
        context.put("time", TimeUtils.class);
        context.put("string", StringUtils.class);
    }

    public static class DomGenerator {

        private static final AtomicInteger counter = new AtomicInteger(1);

        public static String nextId() {
            return "element_" + counter.getAndIncrement();
        }
    }

    static class VelocityLogger implements LogChute {

        public void init(RuntimeServices rs) throws Exception {
        }

        private String getStackTrace(Throwable t) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            return sw.toString();
        }

        public void log(int level, String message) {
            switch (level) {
                case LogChute.DEBUG_ID:
                    LOGGER.finer(message);
                    break;
                case LogChute.INFO_ID:
                    LOGGER.info(message);
                    break;
                case LogChute.WARN_ID:
                    LOGGER.warning(message);
                    break;
                case LogChute.ERROR_ID:
                    LOGGER.severe(message);
                    break;
            }

        }

        public void log(int level, String message, Throwable t) {
            switch (level) {
                case LogChute.TRACE_ID:
                    LOGGER.finer(message + getStackTrace(t));
                    break;
                case LogChute.DEBUG_ID:
                    LOGGER.finer(message + getStackTrace(t));
                    break;
                case LogChute.INFO_ID:
                    LOGGER.info(message + getStackTrace(t));
                    break;
                case LogChute.WARN_ID:
                    LOGGER.warning(message + getStackTrace(t));
                    break;
                case LogChute.ERROR_ID:
                    LOGGER.severe(message + getStackTrace(t));
                    break;
            }
        }

        public boolean isLevelEnabled(int level) {
            switch (level) {
                case LogChute.TRACE_ID:
                    return LOGGER.isLoggable(Level.FINE);
                case LogChute.DEBUG_ID:
                    return LOGGER.isLoggable(Level.FINE);
                case LogChute.INFO_ID:
                    return LOGGER.isLoggable(Level.INFO);
                case LogChute.WARN_ID:
                    return LOGGER.isLoggable(Level.WARNING);
                case LogChute.ERROR_ID:
                    return LOGGER.isLoggable(Level.SEVERE);
            }
            return false;
        }
    }

    public static class ClassPathResourceLoader extends ResourceLoader {

        public void init(ExtendedProperties configuration) {
            setCachingOn(false);
        }

        public InputStream getResourceStream(String source) throws ResourceNotFoundException {
            LOGGER.fine("Load resource '" + source + "'");
            try {
                URL resourceURL = getResourceURL(source);
                if (resourceURL == null) {
                    return null;
                }
                return resourceURL.openStream();
            } catch (IOException e) {
                throw new ResourceNotFoundException("Failed to locate resource: " + source);
            }
        }

        public boolean isSourceModified(Resource resource) {
            return false;
        }

        public long getLastModified(Resource resource) {
            return 0;
        }

        private URL getResourceURL(String name) {
            return getClass().getClassLoader().getResource("template/" + name);
        }
    }

}
