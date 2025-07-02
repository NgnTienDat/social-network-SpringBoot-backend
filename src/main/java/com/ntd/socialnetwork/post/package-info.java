@ApplicationModule(allowedDependencies = {"user", "user :: model", "common :: response", "common :: exception",
        "user :: response",  "user :: service"})
package com.ntd.socialnetwork.post;

import org.springframework.modulith.ApplicationModule;