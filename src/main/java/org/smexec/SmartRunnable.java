package org.smexec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A wrapper Runnable for internal usage. <br>
 * I allows us to better control the thread execution, hooks and thread naming.
 */
public class SmartRunnable
    implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(SmartRunnable.class);

    private Runnable runnable;
    private String threadNameSuffix;

    public SmartRunnable(Runnable runnable, String threadNameSuffix) {
        this.runnable = runnable;
        this.threadNameSuffix = threadNameSuffix;
    }

    public SmartRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void run() {
        String orgName = null;
        try {
            if (threadNameSuffix != null) {
                orgName = Thread.currentThread().getName();
                Thread.currentThread().setName(orgName + "_" + threadNameSuffix);
            }
            // TODO Pre execution HOOK

            runnable.run();

            // TODO Post execution HOOK

        } catch (Exception e) {
            logger.error(e.getMessage(), e);

        } finally {
            if (orgName != null) {
                Thread.currentThread().setName(orgName);
            }
        }
    }
    
}
