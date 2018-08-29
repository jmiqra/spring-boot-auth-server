INSERT INTO oauth_client_details
	(client_id, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	('sampleClientId', 'secret', 'read,write,foo,bar',
	'implicit', null, null, 36000, 36000, null, 'false');

INSERT INTO oauth_client_details
	(client_id, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	('postman-client', 
	/*1234*/'$2a$04$EDhc4iSrKvJn1USNPoAO/..ao60m11nc7lnMn8CighIrW6p9fAMmC', 
	'read,write,delete',
	'password,authorization_code,refresh_token,implicit', 'https://www.getpostman.com/oauth2/callback', null, 36000, 36000, null, 'true');
