package legacyfighter.leave;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeaveServiceTest {

    static final long ONE = 1L;

    @InjectMocks
    LeaveService leaveService;

    @Mock
    LeaveDatabase database;

    @Mock
    EscalationManager escalationManager;

    @Mock
    MessageBus messageBus;

    @Mock
    EmailSender emailSender;

    @Test
    void requests_of_performers_will_be_manually_processed_after_26th_day() {
        //given
        when(database.findByEmployeeId(ONE)).thenReturn(new Object[]{"PERFORMER", 10});

        //when
        Result result = leaveService.requestPaidDaysOff(30, ONE);

        //then
        assertEquals(Result.Manual, result);
        Mockito.verify(escalationManager).notifyNewPendingRequest(ONE);
        Mockito.verifyNoInteractions(emailSender);
        Mockito.verifyNoInteractions(messageBus);
        Mockito.verifyNoMoreInteractions(database);
    }

    @Test
    void performers_cannot_get_more_than_45_days() {
        //given
        when(database.findByEmployeeId(ONE)).thenReturn(new Object[]{"PERFORMER", 10});

        //when
        Result result = leaveService.requestPaidDaysOff(50, ONE);

        //then
        assertEquals(Result.Denied, result);
        Mockito.verify(emailSender).send("next time");
        Mockito.verifyNoInteractions(escalationManager);
        Mockito.verifyNoInteractions(messageBus);
        Mockito.verifyNoMoreInteractions(database);


    }

    @Test
    void slackers_do_not_get_any_leave() {

        //given
        when(database.findByEmployeeId(ONE)).thenReturn(new Object[]{"SLACKER", 10});

        //when
        Result result = leaveService.requestPaidDaysOff(1, ONE);

        //then
        assertEquals(Result.Denied, result);


    }

    @Test
    void slackers_get_a_nice_email() {

        //given
        when(database.findByEmployeeId(ONE)).thenReturn(new Object[]{"SLACKER", 10});

        //when
        leaveService.requestPaidDaysOff(1, ONE);

        //then
        Mockito.verify(emailSender).send("next time");


    }

    @Test
    void regular_employee_doesnt_get_more_than_26_days() {
        //given
        when(database.findByEmployeeId(ONE)).thenReturn(new Object[]{"REGULAR", 10});

        //when
        Result result = leaveService.requestPaidDaysOff(20, ONE);

        //then
        assertEquals(Result.Denied, result);
        Mockito.verify(emailSender).send("next time");
        Mockito.verifyNoInteractions(escalationManager);
        Mockito.verifyNoInteractions(messageBus);
        Mockito.verifyNoMoreInteractions(database);

    }


    @Test
    void regular_employee_gets_26_days() {
        //given
        when(database.findByEmployeeId(ONE)).thenReturn(new Object[]{"REGULAR", 10});

        //when
        Result result = leaveService.requestPaidDaysOff(5, ONE);

        //then
        assertEquals(Result.Approved, result);
        Mockito.verify(messageBus).sendEvent("request approved");
        Mockito.verifyNoInteractions(escalationManager);
        Mockito.verifyNoInteractions(emailSender);
        Mockito.verify(database).save(new Object[]{"REGULAR", 15});

    }


}