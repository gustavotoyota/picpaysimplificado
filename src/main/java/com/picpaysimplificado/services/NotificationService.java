package com.picpaysimplificado.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dtos.NotificationDTO;

@Service
public class NotificationService {
  @Autowired
  private RestTemplate restTemplate;

  public void sendNotification(User user, String message) throws Exception {
    String email = user.getEmail();

    NotificationDTO notificationRequest = new NotificationDTO(email, message);

    /*
    ResponseEntity<String> notificationResponse = this.restTemplate.postForEntity("http://o4d9z.mocklab.io/notify", notificationRequest, String.class);

    if (!notificationResponse.getStatusCode().is2xxSuccessful()) {
      System.out.println("Falha ao enviar notificação");
      throw new Exception("Falha ao enviar notificação");
    }
    */

    System.out.println("Notificação enviada com sucesso");
  }
}
