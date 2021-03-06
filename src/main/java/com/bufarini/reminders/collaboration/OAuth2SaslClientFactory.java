/*
Copyright 2015 Daniele Bufarini

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.bufarini.reminders.collaboration;

import android.util.Log;
import myjavax.security.auth.callback.CallbackHandler;
import myjavax.security.sasl.SaslClient;
import myjavax.security.sasl.SaslClientFactory;

import java.util.Map;

/*A SaslClientFactory that returns instances of OAuth2SaslClient.
 *
 * <p>Only the "XOAUTH2" mechanism is supported. The {@code callbackHandler} is
 * passed to the OAuth2SaslClient. Other parameters are ignored.
 * */

public class OAuth2SaslClientFactory implements SaslClientFactory {
	private static final String LOGTAG = OAuth2SaslClientFactory.class.getSimpleName();
	private static final String[] MECHANISM_NAMES = new String[] { "XOAUTH2" };
	
	public static final String OAUTH_TOKEN_PROP = "mail.imaps.sasl.mechanisms.oauth2.oauthToken";

	@Override
	public SaslClient createSaslClient(String[] mechanisms, String authorizationId,
			String protocol, String serverName, Map<String, ?> props,
			CallbackHandler callbackHandler)
	{
		boolean matchedMechanism = false;
		for (int i = 0; i < mechanisms.length; ++i) {
			if ("XOAUTH2".equalsIgnoreCase(mechanisms[i])) {
				matchedMechanism = true;
				break;
			}
		}
		if (!matchedMechanism) {
			Log.i(LOGTAG, "Failed to match any mechanisms");
			return null;
		}
		return new OAuth2SaslClient((String) props.get(OAUTH_TOKEN_PROP), callbackHandler);
	}

	public String[] getMechanismNames(Map<String, ?> props) {
		return MECHANISM_NAMES;
	}
}