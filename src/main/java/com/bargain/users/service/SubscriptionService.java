package com.bargain.users.service;

//import com.bargain.notification.client.NotificationClient;
//import com.bargain.notification.client.dto.request.SendNotificationRequest;
import com.bargain.users.client.dto.SubscriptionStatus;
import com.bargain.users.model.User;
import com.bargain.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

//    @Autowired
//    private NotificationClient notificationClient;

    @Value("${notification-service.enabled}")
    private Boolean notificationServiceEnabled;

    public SubscriptionStatus getSubscriptionForUser(String userRef) {
        User user = userService.getByRef(userRef);
        return user.getSubscription();
    }

    public SubscriptionStatus subscribe(String userRef, SubscriptionStatus subscriptionStatus) {
        User user = userService.getByRef(userRef);
        user.setSubscription(subscriptionStatus);
        userRepository.save(user);

        sendNotificationIfEnabled(
                String.format("Congrats! You've just picked up the %s subscription!", subscriptionStatus.name()),
                userRef);

        return user.getSubscription();
    }

    public void cancel(String userRef) {
        User user = userService.getByRef(userRef);
        user.setSubscription(null);
        userRepository.save(user);

        sendNotificationIfEnabled("You've just cancelled your subscription. We hope to see you again one day!",
                userRef);
    }

    private void sendNotificationIfEnabled(String message, String userRef) {
        return;
        //        if (notificationServiceEnabled) {
//            notificationClient.send(SendNotificationRequest.builder()
//                    .message(message)
//                    .userReference(userRef)
//                    .build());
//        }
    }
}
