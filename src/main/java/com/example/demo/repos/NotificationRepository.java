package com.example.demo.repos;

import com.example.demo.models.Notification;
import com.example.demo.models.NotificationStatus;
import com.example.demo.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {
    public Optional<Notification> findById (Long id );
    public List<Notification> findAllByStatus(NotificationStatus status);
    public List<Notification> findAllByOrderByCreatedAtDesc();
    public List<Notification> findAllByStatusOrderByCreatedAtDesc(NotificationStatus notificationStatus);
    public List<Notification> findTop5ByStatusAndNotifiedTo(NotificationStatus status, User notifiedTo);
    public List<Notification> findAllByNotifiedToOrderByCreatedAtDesc(User notifiedTo);
    public List<Notification> countAllByIdAndStatus(Long id, NotificationStatus status);
}

