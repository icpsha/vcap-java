package org.cloudfoundry.reconfiguration.data.document;

import org.cloudfoundry.reconfiguration.AbstractServiceConfigurer;
import org.cloudfoundry.runtime.env.CloudEnvironment;
import org.cloudfoundry.runtime.env.MongoServiceInfo;
import org.cloudfoundry.runtime.service.AbstractServiceCreator;
import org.cloudfoundry.runtime.service.document.MongoServiceCreator;
import org.springframework.data.mongodb.MongoDbFactory;

/**
 * Implementation of {@link AbstractServiceConfigurer} that replaces a single
 * {@link MongoDbFactory} with one connecting to a mongo cloud service bound to
 * the current application.
 *
 * @author Jennifer Hickey
 *
 */
public class MongoConfigurer extends AbstractServiceConfigurer<MongoServiceInfo> {

	static final String CF_MONGO_DB_FACTORY_NAME = "__cloudFoundryMongoDbFactory";

	private static final String MONGO_DB_FACTORY_CLASS_NAME = "org.springframework.data.mongodb.MongoDbFactory";

	public MongoConfigurer(CloudEnvironment cloudEnvironment) {
		super(cloudEnvironment, MongoServiceInfo.class);
	}

	@Override
	public String getBeanClass() {
		return MONGO_DB_FACTORY_CLASS_NAME;
	}

	@Override
	public String getServiceBeanName() {
		return CF_MONGO_DB_FACTORY_NAME;
	}

	@Override
	public AbstractServiceCreator<?, MongoServiceInfo> getServiceCreator() {
		return new MongoServiceCreator();
	}
}
