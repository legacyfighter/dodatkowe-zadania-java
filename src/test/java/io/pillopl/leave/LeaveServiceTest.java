package io.pillopl.leave;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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


    }

    @Test
    void performers_cannot_get_more_than_45_days() {


    }

    @Test
    void slackers_do_not_get_any_leave() {


    }

    @Test
    void regular_employee_doesnt_get_more_than_26_days() {

    }


    @Test
    void regular_employee_gets_26_days() {

    }


}