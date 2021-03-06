/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.transaction.annotation;

import org.springframework.context.annotation.AutoProxyRegistrar;
import org.springframework.nativex.extension.NativeImageHint;
import org.springframework.nativex.extension.NativeImageConfiguration;
import org.springframework.nativex.extension.TypeInfo;
import org.springframework.nativex.type.AccessBits;
import org.springframework.transaction.aspectj.AspectJJtaTransactionManagementConfiguration;
import org.springframework.transaction.aspectj.AspectJTransactionManagementConfiguration;

// TODO really the 'skip if missing' can be different for each one here...
@NativeImageHint(trigger=TransactionManagementConfigurationSelector.class, typeInfos = {
	@TypeInfo(types= {
			AutoProxyRegistrar.class,
			ProxyTransactionManagementConfiguration.class,
			AspectJJtaTransactionManagementConfiguration.class,
			AspectJTransactionManagementConfiguration.class
	}, typeNames = "org.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor$1", access = AccessBits.LOAD_AND_CONSTRUCT)
}, follow=true)
@NativeImageHint(typeInfos= {
		@TypeInfo(types= {
				Transactional.class,
				javax.transaction.Transactional.class
		},access=AccessBits.CLASS|AccessBits.DECLARED_METHODS),
		@TypeInfo(types= Propagation.class,access=AccessBits.CLASS|AccessBits.DECLARED_METHODS|AccessBits.DECLARED_FIELDS) // TODO this is an enum - we can probably infer what access an enum requires if exposed
		})
public class TransactionManagementHints implements NativeImageConfiguration {
}
