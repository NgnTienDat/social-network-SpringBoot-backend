@ApplicationModule(allowedDependencies = {"user", "user :: model", "user :: enums", "common :: exception",
        "common :: response", "user :: service"})
package com.ntd.socialnetwork.security;

import org.springframework.modulith.ApplicationModule;