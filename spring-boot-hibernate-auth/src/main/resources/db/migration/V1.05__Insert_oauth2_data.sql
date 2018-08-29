INSERT INTO OAUTH_CLIENT_DETAILS(CLIENT_ID, RESOURCE_IDS, CLIENT_SECRET, SCOPE, AUTHORIZED_GRANT_TYPES, AUTHORITIES, ACCESS_TOKEN_VALIDITY, REFRESH_TOKEN_VALIDITY)
	VALUES ('spring-security-oauth2-read-client', 'resource-server-rest-api',
	/*spring-security-oauth2-read-client-password1234*/'$2a$04$WGq2P9egiOYoOFemBRfsiO9qTcyJtNRnPKNBl5tokP7IP.eZn93km',
	'read', 'password,authorization_code,refresh_token,implicit', 'USER', 10800, 2592000);

INSERT INTO OAUTH_CLIENT_DETAILS(CLIENT_ID, RESOURCE_IDS, CLIENT_SECRET, SCOPE, AUTHORIZED_GRANT_TYPES, AUTHORITIES, ACCESS_TOKEN_VALIDITY, REFRESH_TOKEN_VALIDITY)
	VALUES ('spring-security-oauth2-read-write-client', 'resource-server-rest-api',
	/*spring-security-oauth2-read-write-client-password1234*/'$2a$04$soeOR.QFmClXeFIrhJVLWOQxfHjsJLSpWrU1iGxcMGdu.a5hvfY4W',
	'read,write', 'password,authorization_code,refresh_token,implicit', 'USER', 10800, 2592000);

INSERT INTO `myauthdb`.`oauth_client_details` 
	(`CLIENT_ID`, `RESOURCE_IDS`, `CLIENT_SECRET`, `SCOPE`, `AUTHORIZED_GRANT_TYPES`, `WEB_SERVER_REDIRECT_URI`, `AUTHORITIES`, `ACCESS_TOKEN_VALIDITY`, `REFRESH_TOKEN_VALIDITY`,`AUTOAPPROVE`) 
	VALUES ('postman-client', 'resource-server-rest-api', 
	/*1234*/'$2a$04$EDhc4iSrKvJn1USNPoAO/..ao60m11nc7lnMn8CighIrW6p9fAMmC', 
	'read,write,delete', 'password,authorization_code,refresh_token,implicit', 'https://www.getpostman.com/oauth2/callback', null, '10800', '2592000','true');
