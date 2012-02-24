package org.cloudfoundry.osgi.reconfiguration;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.cloudfoundry.reconfiguration.CloudAutoStagingBeanFactoryPostProcessor;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.InvalidSyntaxException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.osgi.extender.OsgiBeanFactoryPostProcessor;

public class CloudAutoStagingOsgiBFPostProcessor implements OsgiBeanFactoryPostProcessor, Ordered {

	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	public void postProcessBeanFactory(BundleContext arg0,
			ConfigurableListableBeanFactory arg1) throws BeansException,
			InvalidSyntaxException, BundleException {
		logger.log(Level.INFO,"Stager is invoked");
		CloudAutoStagingBeanFactoryPostProcessor processor = new CloudAutoStagingBeanFactoryPostProcessor();
		processor.postProcessBeanFactory(arg1);
		
	
			
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return LOWEST_PRECEDENCE;
	}



}
