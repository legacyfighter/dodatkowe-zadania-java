package legacyfighter.leave;

public class LeaveService {

    final LeaveDatabase database;
    final MessageBus messageBus;
    final EmailSender emailSender;
    final EscalationManager escalationManager;
    final Configuration configuration;

    LeaveService(LeaveDatabase database, MessageBus messageBus, EmailSender emailSender, EscalationManager escalationManager, Configuration configuration) {
        this.database = database;
        this.messageBus = messageBus;
        this.emailSender = emailSender;
        this.escalationManager = escalationManager;
        this.configuration = configuration;
    }

    public Result requestPaidDaysOff(int days, Long employeeId) {
        if (days < 0) {
            throw new IllegalArgumentException();
        }

        Employee employee = database.findByEmployeeId(employeeId);

        Result result = employee.requestDaysOff(days);

        if(result == Result.Manual) {
            escalationManager.notifyNewPendingRequest(employeeId);
        }

        if (result == Result.Denied) {
            emailSender.send("next time");
        }

        if (result == Result.Approved) {
            messageBus.sendEvent("request approved");
            database.save(employee);

        }
        return result;
    }


}

class Employee {
    private Long employeeId;
    private String employeeStatus;
    private int daysSoFar;

    public Employee(Long employeeId, String employeeStatus, int daysSoFar) {
        this.employeeId = employeeId;
        this.employeeStatus = employeeStatus;
        this.daysSoFar = daysSoFar;
    }

    public Result requestDaysOff(int days) {
        if (daysSoFar + days > 26) {

            if (employeeStatus.equals("PERFORMER") && daysSoFar + days < 45) {
                return Result.Manual;
            } else {
                return Result.Denied;
            }

        } else {

            if (employeeStatus.equals("SLACKER")) {
                return Result.Denied;
            } else {
                daysSoFar = daysSoFar + days;
                return Result.Approved;
            }
        }

    }
}

class LeaveDatabase {

    Employee findByEmployeeId(Long employeeId) {
        return null;
    }

    void save(Employee employeeData) {

    }
}

class MessageBus {

    void sendEvent(String msg) {
    }
}

class EmailSender {

    void send(String msg) {
    }
}

class EscalationManager {

    void notifyNewPendingRequest(Long employeeId) {
    }
}

class Configuration {

    int getMaxDaysForPerformers() {
        return 45;
    }

    int getMaxDays() {
        return 26;
    }

}