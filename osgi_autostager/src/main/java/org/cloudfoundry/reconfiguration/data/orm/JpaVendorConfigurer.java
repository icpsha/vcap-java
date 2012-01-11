package org.cloudfoundry.reconfiguration.data.orm;

import org.cloudfoundry.reconfiguration.Configurer;
import org.cloudfoundry.runtime.env.CloudEnvironment;
import org.cloudfoundry.runtime.env.RdbmsServiceInfo;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.Assert;

public class JpaVendorConfigurer implements Configurer {

	private CloudEnvironment cloudEnvironment;

	public JpaVendorConfigurer(CloudEnvironment cloudEnvironment) {
		this.cloudEnvironment = cloudEnvironment;
	}
	@Override
	public boolean configure(DefaultListableBeanFactory beanFactory) {
		// TODO Auto-generated method stub
		boolean configured = false;
		String database=null;
		for (RdbmsServiceInfo service : cloudEnvironment.getServiceInfos(RdbmsServiceInfo.class)) {
			if (service.getLabel().startsWith("postgresql")) 
				database = "postgresql";				
			 else if (service.getLabel().startsWith("mysql")) 
				 database = "mysql";
			Assert.notNull(database);
			configureJpaVendorAdapter(beanFactory,database);
			configureEclipseLinkVendorAdapter(beanFactory,database);
			configureHibernateVendorAdapter(beanFactory,database);
			
		}
			
		return configured;
	}

	private void configureJpaVendorAdapter(DefaultListableBeanFactory beanFactory,String database)
	{
		String dbplatform = null;
		if(database.equals("mysql"))
			dbplatform = "org.apache.openjpa.jdbc.sql.MySQLDictionary";
		else if(database.equals("postgresql"))
			dbplatform=  "org.apache.openjpa.jdbc.sql.PostgresDictionary";
		Assert.notNull(dbplatform);
		updateBeanProperty(beanFactory,"org.springframework.orm.jpa.vendor.OpenJpaVendorAdapter",
					"databasePlatform", dbplatform);
		
		
	}
	private void configureEclipseLinkVendorAdapter(DefaultListableBeanFactory beanFactory,String database)
	{
		String dbplatform = null;
		if(database.equals("mysql"))
			dbplatform = "org.eclipse.persistence.platform.database.MySQLPlatform";
		else if(database.equals("postgresql"))
			dbplatform=  "org.eclipse.persistence.platform.database.PostgreSQLPlatform";
		Assert.notNull(dbplatform);
		updateBeanProperty( beanFactory,"org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter",
					"databasePlatform", dbplatform);
			
	}
	
	private void configureHibernateVendorAdapter(DefaultListableBeanFactory beanFactory,String database)
	{
		String dbplatform = null;
		if(database.equals("mysql"))
			dbplatform = "org.hibernate.dialect.MySQL5Dialect";
		else if(database.equals("postgresql"))
			dbplatform=  "org.hibernate.dialect.PostgreSQLDialect";
		Assert.notNull(dbplatform);
		updateBeanProperty(beanFactory,"org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter",
					"databasePlatform", dbplatform);
			
	}
	private void updateBeanProperty(DefaultListableBeanFactory beanFactory,String beanClass, String propertyKey,String propertyValue){
		try {
				String[] beans = beanFactory.getBeanNamesForType(Class.forName(beanClass));
				//Should there be only one JPAvendorAdapter??
				for(String bean:beans)
				{
					BeanDefinition beanDef = beanFactory.getBeanDefinition(bean);
					beanDef.getPropertyValues().removePropertyValue(propertyKey);
					beanDef.getPropertyValues().addPropertyValue(propertyKey, propertyValue);			
					
				}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
