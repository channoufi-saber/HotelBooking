package com.hotel.notification;

import com.hotel.dtos.NotificationDTO;

public interface NotificationService {
	void sendEmail(NotificationDTO notificationDTO);
	void sensSms();
	void sendWhatsapp();
}
