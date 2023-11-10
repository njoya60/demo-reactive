package com.wf.br.router;

import com.wf.br.handler.SocieteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {
    @Autowired
    SocieteHandler societeHandler;

    @Bean
    RouterFunction<ServerResponse>routerFunction(){
        return RouterFunctions.route()
                .GET("/router/societe", societeHandler::loadSocietes)
                .GET("/router/societe-stream",societeHandler::getSocietesStream)
                .GET("/router/societe-stream/{input}",societeHandler::findBySocietes)
                .POST("/router/societe-stream/save",societeHandler::saveSociete)
                .build();
    }
}
