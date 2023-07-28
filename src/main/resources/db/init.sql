CREATE TABLE "sys_user" (
                            "id" integer NOT NULL PRIMARY KEY AUTOINCREMENT,
                            "login_code" TEXT,
                            "password" TEXT
);
INSERT INTO "sys_user"("id", "login_code", "password") VALUES (1, 'admin', '$2a$10$UWsbRm7hg1JD4s7OxD5aLuwBMfs0zlqv3k3dtpvwhWIINghJpvUji');
