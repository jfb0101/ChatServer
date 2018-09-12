/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.treinarinformatica.chatserver.service;

import br.com.treinarinformatica.chatserver.model.Chat;
import br.com.treinarinformatica.chatserver.util.HibernateUtil;
import com.google.protobuf.ServiceException;
import org.hibernate.Session;

/**
 *
 * @author Treinar
 */
public class ChatService {
    public void save(Chat chat) throws ServiceException {
        try (Session session = HibernateUtil.sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(chat);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
