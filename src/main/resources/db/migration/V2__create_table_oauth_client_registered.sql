CREATE TABLE oauth2_registered_client
(
    id                            varchar(36) primary key,
    client_id                     varchar(100)  not null,
    client_id_issued_at           timestamp     not null default current_timestamp,
    client_secret                 varchar(200),
    client_secret_expires_at      timestamp,
    client_name                   varchar(200)  not null,
    client_authentication_methods varchar(1000) not null,
    authorization_grant_types     varchar(1000) not null,
    redirect_uris                 varchar(1000),
    scopes                        varchar(1000) not null,
    client_settings               text          not null,
    token_settings                text          not null,
    post_logout_redirect_uris      varchar(1000)
);

create unique index ux_oauth2_registered_client_client_id on oauth2_registered_client (client_id);


INSERT INTO oauth2_registered_client
(id,
 client_id,
 client_id_issued_at,
 client_secret,
 client_secret_expires_at,
 client_name,
 client_authentication_methods,
 authorization_grant_types,
 redirect_uris,
 scopes,
 client_settings,
 token_settings,
 post_logout_redirect_uris)
VALUES ('e1fe0c76-870e-447b-bf6f-a7cea9085685'::uuid,
        'client-service-app',
        current_timestamp,
        '$2a$10$043CxbVQqNTGV4IX6XX.Cu4I4FntbVqoiSy/6CH6GgaDtXrEgiBk2',
        NULL,
        'Service App',
        'client_secret_basic',
        'client_credentials',
        '',
        'api.write,api.read',
        '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}',
        '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,"settings.token.x509-certificate-bound-access-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",3600.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",2592000.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.device-code-time-to-live":["java.time.Duration",300.000000000]}',
        '');


INSERT INTO oauth2_registered_client
(id,
 client_id,
 client_id_issued_at,
 client_secret,
 client_secret_expires_at,
 client_name,
 client_authentication_methods,
 authorization_grant_types,
 redirect_uris,
 scopes,
 client_settings,
 token_settings,
 post_logout_redirect_uris)
VALUES ('c0e12ecc-8a1e-4255-89e6-727c478a9e13',
        'client-web-app',
        current_timestamp,
        '$2a$10$f0q3Oue0fMYBPavPvw318OIJF6SJZqscCt.hestzFbv2GHVlQqqp6',
        NULL,
        'Web App',
        'client_secret_basic',
        'authorization_code',
        'http://localhost:8080/authorized',
        'api.write,api.read',
        '{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":false}',
        '{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":false,"settings.token.x509-certificate-bound-access-tokens":false,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.access-token-time-to-live":["java.time.Duration",3600.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",2592000.000000000],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.device-code-time-to-live":["java.time.Duration",300.000000000]}',
        '');