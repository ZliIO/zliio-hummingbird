package com.zliio.hummingbird.web.factory;

import com.zliio.hummingbird.core.factory.ClassFactory;
import com.zliio.hummingbird.web.annotation.*;
import com.zliio.hummingbird.web.common.entity.RequestMappingDetail;
import com.zliio.hummingbird.web.common.lang.HttpMethod;
import com.zliio.hummingbird.web.common.util.UrlUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 路由与控制器方法的映射
 *
 * @author ZLiIO
 * @since 1.0 (2023-05-16)
 **/
public class RouteMethodMapper {
    /**
     * request url -> target request method.
     * eg: "^/rest/[\u4e00-\u9fa5_a-zA-Z0-9]+/?$" -> RestController.get(java.lang.Integer)
     */
    private static final Map<HttpMethod, Map<String, RequestMappingDetail>> REQUEST_METHOD_MAPPINGS = new HashMap<>(8);

    /**
     * formatted get request url -> original url
     * eg : "^/url/[\u4e00-\u9fa5_a-zA-Z0-9]+/?$" -> /url/{id}
     */
    private static final Map<HttpMethod, Map<Pattern, String>> REQUEST_URL_MAPPINGS = new HashMap<>(8);


    private static void init(){
        REQUEST_METHOD_MAPPINGS.put(HttpMethod.GET,new HashMap<>(128));
        REQUEST_METHOD_MAPPINGS.put(HttpMethod.POST,new HashMap<>(128));
        REQUEST_METHOD_MAPPINGS.put(HttpMethod.DELETE,new HashMap<>(128));
        REQUEST_METHOD_MAPPINGS.put(HttpMethod.PUT,new HashMap<>(128));

        REQUEST_URL_MAPPINGS.put(HttpMethod.GET,new HashMap<>(128));
        REQUEST_URL_MAPPINGS.put(HttpMethod.POST,new HashMap<>(128));
        REQUEST_URL_MAPPINGS.put(HttpMethod.DELETE,new HashMap<>(128));
        REQUEST_URL_MAPPINGS.put(HttpMethod.PUT,new HashMap<>(128));
    }

    public static void loadRoutes() {
        init();
        Set<Class<?>> classes = ClassFactory.CLASSES.get(RestController.class);
        for (Class<?> aClass : classes) {
            RestController restController = aClass.getAnnotation(RestController.class);
            if (null != restController) {
                Method[] methods = aClass.getDeclaredMethods();
                String rootUrl = restController.value();
                for (Method method : methods) {
                    Annotation[] annotations = method.getAnnotations();
                    if(null == annotations ||0 == annotations.length){
                        continue;
                    }
                    List<RequestMappingDetail> requestMappingDetailList = buildRequestMappingDetailListByMethod(method);
                    if(requestMappingDetailList.isEmpty()){
                        continue;
                    }
                    for (RequestMappingDetail requestMappingDetail : requestMappingDetailList) {
                        if(!"".equals(rootUrl)){
                            requestMappingDetail.setPath(Arrays.stream(
                                    requestMappingDetail.getPath())
                                    .map(path-> String.format("%s/%s",rootUrl,path))
                                    .toArray(String[]::new));
                        }
                        if(requestMappingDetail.getPath().length == 0){
                            String path = "/";
                            String formatUrl = UrlUtil.formatUrl(path);
                            REQUEST_METHOD_MAPPINGS.get(requestMappingDetail.getHttpMethod()).put(formatUrl,requestMappingDetail);
                            Pattern pattern = Pattern.compile(formatUrl);
                            REQUEST_URL_MAPPINGS.get(requestMappingDetail.getHttpMethod()).put(pattern,path);
                        }else{
                            for (String path : requestMappingDetail.getPath()) {
                                REQUEST_METHOD_MAPPINGS.get(requestMappingDetail.getHttpMethod()).put(path,requestMappingDetail);
                                Pattern pattern = Pattern.compile(UrlUtil.formatUrl(path));
                                REQUEST_URL_MAPPINGS.get(requestMappingDetail.getHttpMethod()).put(pattern,path);
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean isRequestMappingAnnotation(Annotation annotation){
        return annotation instanceof GetMapping || annotation instanceof PostMapping
                || annotation instanceof PutMapping || annotation instanceof DeleteMapping;

    }

    private static List<RequestMappingDetail> buildRequestMappingDetailListByMethod(Method method){
        List<RequestMappingDetail> requestMappingDetailList = new ArrayList<>();
        for (Annotation annotation : method.getAnnotations()) {
            if(isRequestMappingAnnotation(annotation)){
                RequestMappingDetail.Builder requestMappingDetailBuilder =  RequestMappingDetail.newBuilder();
                if(annotation instanceof GetMapping){
                    requestMappingDetailBuilder.httpMethod(HttpMethod.GET)
                            .consumes(((GetMapping) annotation).consumes())
                            .headers(((GetMapping) annotation).headers())
                            .produces(((GetMapping) annotation).produces())
                            .name(((GetMapping) annotation).name())
                            .path(((GetMapping) annotation).path());
                }else if(annotation instanceof PutMapping){
                    requestMappingDetailBuilder.httpMethod(HttpMethod.PUT)
                            .consumes(((PutMapping) annotation).consumes())
                            .headers(((PutMapping) annotation).headers())
                            .produces(((PutMapping) annotation).produces())
                            .name(((PutMapping) annotation).name())
                            .path(((PutMapping) annotation).path());
                }else if(annotation instanceof PostMapping){
                    requestMappingDetailBuilder.httpMethod(HttpMethod.POST)
                            .consumes(((PostMapping) annotation).consumes())
                            .headers(((PostMapping) annotation).headers())
                            .produces(((PostMapping) annotation).produces())
                            .name(((PostMapping) annotation).name())
                            .path(((PostMapping) annotation).path());
                }else if(annotation instanceof DeleteMapping){
                    requestMappingDetailBuilder.httpMethod(HttpMethod.DELETE)
                            .consumes(((DeleteMapping) annotation).consumes())
                            .headers(((DeleteMapping) annotation).headers())
                            .produces(((DeleteMapping) annotation).produces())
                            .name(((DeleteMapping) annotation).name())
                            .path(((DeleteMapping) annotation).path());
                }
                requestMappingDetailBuilder.targetMethod(method);
                requestMappingDetailList.add(requestMappingDetailBuilder.build());
            }
        }
        return requestMappingDetailList;
    }


    public static RequestMappingDetail getRequestMappingDetail(String requestPath, HttpMethod httpMethod) {
        String url = "/404";
        for (Pattern pattern : REQUEST_URL_MAPPINGS.get(httpMethod).keySet()) {
            if(pattern.matcher(requestPath).find()){
                url = REQUEST_URL_MAPPINGS.get(httpMethod).get(pattern);
            }
        }
        return REQUEST_METHOD_MAPPINGS.get(httpMethod).get(url);
    }
}
