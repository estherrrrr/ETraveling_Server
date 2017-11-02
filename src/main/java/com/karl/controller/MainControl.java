package com.karl.controller;

import com.karl.model.CommentEntity;
import com.karl.model.RouteEntity;
import com.karl.model.UserEntity;
import com.karl.repository.CommentRepository;
import com.karl.repository.RouteRepository;
import com.karl.repository.UserRepository;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by R on 2017/8/22.
 */
@Controller
public class MainControl {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RouteRepository routeRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "/su";
    }

    @ResponseBody
    @RequestMapping(value = "/addComment", method = RequestMethod.GET)
    public String addComment(String commentData) {
        List<CommentEntity> comments = commentRepository.findAll();
        int count = comments.size() + 1;

        JSONObject jsonObject = JSONObject.fromObject(commentData);

        Timestamp ts = new Timestamp(System.currentTimeMillis());
        try {
            ts = Timestamp.valueOf(jsonObject.getString("sendtime"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        CommentEntity c = new CommentEntity();
        c.setId(String.valueOf(count));
        c.setAddId(jsonObject.getString("addId"));
        c.setPlaceId(jsonObject.getString("placeId"));
        c.setLongitude(jsonObject.getString("longitude"));
        c.setOpenid(jsonObject.getString("openid"));
        c.setContent(jsonObject.getString("content"));
        c.setSendtime(ts);
        c.setImg(jsonObject.getString("img"));

        commentRepository.saveAndFlush(c);

        return "/su";
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public String addUser(String userData) {
        List<UserEntity> users = userRepository.findAll();

        JSONObject jsonObject = JSONObject.fromObject(userData);

        int age = Integer.parseInt(jsonObject.getString("age"));
        UserEntity u = new UserEntity();

        for(UserEntity us: users){
            if(us.getOpenid().equals(jsonObject.getString("openid"))){
                userRepository.updateUser(jsonObject.getString("name"), jsonObject.getString("sex"), age, jsonObject.getString("origin"), jsonObject.getString("headimg"), jsonObject.getString("openid"));
                return "/su";
            };
        }
        u.setOpenid(jsonObject.getString("openid"));
        u.setName(jsonObject.getString("name"));
        u.setSex(jsonObject.getString("sex"));
        u.setAge(age);
        u.setOrgin(jsonObject.getString("origin"));
        u.setHeadimg(jsonObject.getString("headimg"));
        userRepository.saveAndFlush(u);

        return "/su";
    }

    @RequestMapping("/getUser")
    @ResponseBody
    public JSONObject get(String openid) {
        Map map = new HashMap();
        List<UserEntity> users = userRepository.findAll();
        for(UserEntity us: users) {
            if (us.getOpenid().equals(openid)) {
                map.put("status",0);
                map.put("openid",us.getOpenid());
                map.put("name",us.getName());
                map.put("sex",us.getSex());
                map.put("age",us.getAge());
                map.put("origin",us.getOrgin());
                map.put("headimg",us.getHeadimg());

                JSONObject json = JSONObject.fromObject(map);
                return json;
            }
        }
        map.put("status",1);

        JSONObject json = JSONObject.fromObject(map);

        return json;
    }

    @RequestMapping("/getComment")
    @ResponseBody
    public JSONObject getComment(String addId) {

        List<CommentEntity> com = commentRepository.findAll();
        List<Map> re = new LinkedList<Map>();

        for(CommentEntity c: com) {
            if (c.getAddId().equals(addId)) {
                Map temp = new HashMap();
                temp.put("addId",c.getAddId());
                temp.put("placeId",c.getPlaceId());
                temp.put("longitude",c.getLongitude());
                temp.put("latitude",c.getLatitude());

                re.add(temp);
            }
        }

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("comments", re);

        return jsonObj;
    }

    @RequestMapping("/getCommentDetail")
    @ResponseBody
    public JSONObject getCommentDetail(String placeId) {
        Map map = new HashMap();
        List<CommentEntity> com = commentRepository.findAll();
        List<UserEntity> usr = userRepository.findAll();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒

        for(CommentEntity c: com) {
            if (c.getPlaceId().equals(placeId)) {
                for(UserEntity u: usr){
                    if(u.getOpenid().equals(c.getOpenid())){
                        map.put("nickName", u.getName());
                        map.put("avatarUrl", u.getHeadimg());
                        break;
                    }
                }
                map.put("content",c.getContent());
                map.put("sendtime",df.format(c.getSendtime()));
                map.put("img",c.getImg());
            }
        }

        JSONObject json = JSONObject.fromObject(map);

        return json;
    }

    @ResponseBody
    @RequestMapping(value = "/addRoute", method = RequestMethod.GET)
    public String addRoute(String routeData) {
        List<RouteEntity> routes = routeRepository.findAll();
        int count = routes.size();
        int order = 0;

        JSONObject jsonObject = JSONObject.fromObject(routeData);

        for(RouteEntity ro: routes){
            if(ro.getOpenid().equals(jsonObject.getString("openid")))
                if(Integer.valueOf(ro.getOrderId()) > order)
                    order = Integer.valueOf(ro.getOrderId());
        }
        order++;

        RouteEntity r = new RouteEntity();

        r.setId(String.valueOf(count));
        r.setOrderId(String.valueOf(order));
        r.setOpenid(jsonObject.getString("openid"));
        r.setLongitude(jsonObject.getString("longitude"));
        r.setLatitude(jsonObject.getString("latitude"));

        routeRepository.saveAndFlush(r);

        return "/su";
    }

    @RequestMapping("/getRoute")
    @ResponseBody
    public JSONObject getRoute(String openid) {

        List<RouteEntity> routes = routeRepository.findAll();
        List<Map> re = new LinkedList<Map>();
        for(RouteEntity ro: routes){
            if (ro.getOpenid().equals(openid)) {
                Map temp = new HashMap();
                temp.put("orderId",ro.getOrderId());
                temp.put("longitude",ro.getLongitude());
                temp.put("latitude",ro.getLatitude());

                re.add(temp);
            }
        }

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("openid", openid);
        jsonObj.put("points", re);

        return jsonObj;
    }
}
