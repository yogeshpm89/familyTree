/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.8).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.family.tree.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@javax.annotation.Generated(value = "com.rfidcollect.codegen.v3.generators.java.SpringCodegen", date = "2019-06-06T16:11:15.514Z[GMT]")
public interface LogoutApi {

    @RequestMapping(value = "/logout",
        method = RequestMethod.POST)
    ResponseEntity<Void> logoutPost();

}