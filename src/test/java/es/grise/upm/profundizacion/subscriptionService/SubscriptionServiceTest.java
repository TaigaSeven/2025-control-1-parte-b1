package es.grise.upm.profundizacion.subscriptionService;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import es.grise.upm.profundizacion.exceptions.ExistingUserException;
import es.grise.upm.profundizacion.exceptions.LocalUserDoesNotHaveNullEmailException;
import es.grise.upm.profundizacion.exceptions.NullUserException;

public class SubscriptionServiceTest {

    private SubscriptionService subscribers;

    @BeforeEach
    public void setUp(){
        subscribers = new SubscriptionService(null);
    }

    //DL - Comprueba que inicialmente no hay usuario suscrito
    @Test
    public void noSuscribedUser(){
        assertEquals(0, subscribers.getSubscribers().size());
    }

    // DL - Comprueba si lanza mensaje cuando el usuario es null
	@Test
    public void nullUserTest(){
        assertThrows(NullUserException.class, () -> {subscribers.addSubscriber(null);});
    }

    //DL - Comprueba si lanza mensaje cuando hay usuario duplicado
    @Test
    public void existingUserTest() throws NullUserException, ExistingUserException, LocalUserDoesNotHaveNullEmailException{
        User u1 = Mockito.mock(User.class);
        Mockito.when(u1.getEmail()).thenReturn("user@test.com");
        subscribers.addSubscriber(u1);
        assertThrows(ExistingUserException.class, () -> {subscribers.addSubscriber(u1);});
    }

    //DL - Comprueba si lanza mensaje cuando un usuario tiene email y delivery local
    @Test
    public void localUserWithoutEmailTest(){
        User u1 = Mockito.mock(User.class);
        Mockito.when(u1.getEmail()).thenReturn("user@test.com");
        Mockito.when(u1.getDelivery()).thenReturn(Delivery.LOCAL);
        assertThrows(LocalUserDoesNotHaveNullEmailException.class, () -> {subscribers.addSubscriber(u1);});
    }


    //DL - Comprueba que los usuarios se pueden añadir suscribir correctamente
    @Test
    public void userSuscribeCorrect() throws NullUserException, ExistingUserException, LocalUserDoesNotHaveNullEmailException{
        final Integer SUBSCRIBERSIZE = 2;
        User u1 = Mockito.mock(User.class);
        Mockito.when(u1.getEmail()).thenReturn(null);
        Mockito.when(u1.getDelivery()).thenReturn(Delivery.LOCAL);

        User u2 = Mockito.mock(User.class);
        Mockito.when(u2.getEmail()).thenReturn("user2@test.com");
        Mockito.when(u2.getDelivery()).thenReturn(Delivery.DO_NOT_DELIVER);

        subscribers.addSubscriber(u1);
        subscribers.addSubscriber(u2);

        assertEquals(SUBSCRIBERSIZE, subscribers.getSubscribers().size());
    }
}
