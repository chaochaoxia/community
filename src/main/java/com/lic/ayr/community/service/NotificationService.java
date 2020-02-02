package com.lic.ayr.community.service;

import com.lic.ayr.community.dto.NotificationDTO;
import com.lic.ayr.community.dto.PaginationDTO;
import com.lic.ayr.community.exception.CustomizeErrorCode;
import com.lic.ayr.community.exception.CustomizeException;
import com.lic.ayr.community.mapper.NotificationMapper;
import com.lic.ayr.community.mapper.QuesstionMapper;
import com.lic.ayr.community.mapper.UserMapper;
import com.lic.ayr.community.model.Notification;
import com.lic.ayr.community.model.Question;
import com.lic.ayr.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuesstionMapper quesstionMapper;


    public PaginationDTO userlist(Integer userid, Integer page, Integer size) {//跟userid相关

        Integer totalCount = notificationMapper.countByUserId(userid);
        if (totalCount==0){
            throw new CustomizeException(CustomizeErrorCode.NOT_FOUND);
        }


        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(totalCount,page,size);
        Integer offset=size*(paginationDTO.getPage()-1);
//        先查全部Notification

        List<Notification> notifications = notificationMapper.userlist(userid,offset,size);


        List<NotificationDTO> notificationDTOS=new ArrayList<>();
        //        再遍历每一个notification
        for (Notification notification : notifications) {

            User user=userMapper.findById(notification.getNotifier());//每一个notification的用户Notifier去找对应的user

            NotificationDTO notificationDTO = new NotificationDTO();//new DTO

            BeanUtils.copyProperties(notification,notificationDTO);//把notification的属性复制给 DTO

            notificationDTO.setNotifier(user);//把user设到DTO中的user
            if (notification.getType()==1){
                Question question = quesstionMapper.getById(notification.getOuterId());
                notificationDTO.setOuterTitle(question.getTitle());
            }else {
                notificationDTO.setOuterTitle("");
            }
            if (notification.getNotifier()!=notification.getReceiver()){
                notificationDTOS.add(notificationDTO);//把遍历的DTO都添加到list中
            }

        }

        paginationDTO.setNotifications(notificationDTOS);
        return paginationDTO;//返回list
    }

    public Integer unreadCount(Integer id) {
        Integer count =notificationMapper.countByUserIdStatus(id);
        return count;
    }

    public NotificationDTO read(Integer id, User user) {

        Notification notification = notificationMapper.selectByKey(id);


        if (notification.getNotifier()==user.getId()){//接受通知的人和用户是同一账号
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        notification.setStatus(1);

        notificationMapper.updataStatus(notification);
        NotificationDTO notificationDTO=new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);


        return notificationDTO;
    }
}
