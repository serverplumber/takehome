package com.example.takehome;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.RouterFunctions;
//import org.springframework.web.reactive.function.server.ServerResponse;
//
//import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
//import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
//
//@Configuration(proxyBeanMethods = false)
//public class FluxRouter {
//
//    @Bean
//    public RouterFunction<ServerResponse> route(ContinentHandler continentHandler) {
//
//        return RouterFunctions
//                .route(POST("/fluxcontinent")
//                        .and(accept(MediaType.APPLICATION_JSON))
//                        , continentHandler::fluxContinent
//                );
//    }
//}