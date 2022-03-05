package com.example.hackathon.server;

import com.example.hackathon.server.dbmappings.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.websocket.WsConnectContext;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Driver {
    private static ObjectMapper objMap;
    private static HashMap<WsConnectContext, String> users = new HashMap<>();
    private static int userNumber = 0;

    public static void main(String[] args) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        objMap = new ObjectMapper();
        Javalin j = Javalin.create(config -> {
        }).start(4000);

        j.ws("/chat", ws -> {
            ws.onConnect(ctx -> {
                String username = "User" + userNumber++;
                users.put(ctx, username);
                broadcastMessage(0,"Server", (username + " joined the chat"));
            });
            ws.onClose(ctx -> {
                String username = users.get(ctx);
                users.remove(ctx);
                broadcastMessage(0, "Server", (username + " left the chat"));
            });
            ws.onMessage(ctx -> {
                if (ctx.message().startsWith("PAGINATION_REQUEST")) {
                    String[] data = ctx.message().split(",");
                    // data[1] has the latest ID that the user has
                    Session session = factory.openSession();
                    Transaction tx = null;

                    ArrayList<com.example.hackathon.server.objects.Message> messages = new ArrayList<>();

                    try {
                        tx = session.beginTransaction();
                        List employees = session.createQuery("FROM Message ").setFirstResult(Integer.parseInt(data[1]) >= 10 ? Integer.parseInt(data[1]) - 10 : 0).setMaxResults(10).list();
                        for (Iterator iterator = employees.iterator(); iterator.hasNext();){
                            Message message = (Message) iterator.next();
                            messages.add(new com.example.hackathon.server.objects.Message(message.getId(), message.getUser(), message.getContent(), users.values().toArray(new String[users.size()])));
                        }
                        tx.commit();
                    } catch (HibernateException e) {
                        if (tx!=null) tx.rollback();
                        e.printStackTrace();
                    } finally {
                        session.close();
                        ctx.send(objMap.writeValueAsString(messages));
                    }
                    return;
                }

                Session session = factory.openSession();
                Transaction tx = null;
                int id;

                try {
                    tx = session.beginTransaction();
                    Message employee = new Message(users.get(ctx), ctx.message());
                    id = (int) session.save(employee);
                    tx.commit();
                    broadcastMessage((int) id, users.get(ctx), ctx.message());
                } catch (HibernateException e) {
                    if (tx!=null) tx.rollback();
                    e.printStackTrace();
                } finally {
                    session.close();
                }
            });
        });
    }

    // Sends a message from one user to all users, along with a list of current usernames
    private static void broadcastMessage(int mid, String sender, String message) {
            users.keySet().stream().filter(ctx -> ctx.session.isOpen()).forEach(session -> {
                try {
                    session.send(
                            objMap.writeValueAsString(new com.example.hackathon.server.objects.Message(mid, message, sender, users.values().toArray(new String[users.size()])))
                    );
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
    }
}