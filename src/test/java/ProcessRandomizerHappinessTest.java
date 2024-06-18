import com.camunda.consulting.Main;
import com.camunda.consulting.facade.ProcessStarterHelper;
import com.camunda.consulting.service.ExampleService;
import com.camunda.consulting.utility.ProcessConstants;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.process.test.api.ZeebeTestEngine;
import io.camunda.zeebe.process.test.assertions.BpmnAssert;
import io.camunda.zeebe.spring.test.ZeebeSpringTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

import static io.camunda.zeebe.process.test.assertions.BpmnAssert.assertThat;
import static io.camunda.zeebe.spring.test.ZeebeTestThreadSupport.waitForProcessInstanceCompleted;
import static io.camunda.zeebe.spring.test.ZeebeTestThreadSupport.waitForProcessInstanceHasPassedElement;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

//Include Main for Deployment Annotation
@SpringBootTest(classes = Main.class)
@ZeebeSpringTest
@Slf4j
public class ProcessRandomizerHappinessTest {

    @Autowired
    private ZeebeTestEngine zeebeTestEngine;

    @Autowired
    private ProcessStarterHelper processStarterHelper;

    @MockBean
    private ExampleService exampleService;

    @Test
    public void testHappyPath() throws InterruptedException, TimeoutException {
        when(exampleService.trueFalseExample()).thenReturn(true);

        ProcessInstanceEvent processInstanceEvent = processStarterHelper.startInstanceSynchronously(ProcessConstants.PROCESS_RANDOMIZE_HAPPINESS_ID);
        assertThat(processInstanceEvent).isStarted();

        waitForProcessInstanceCompleted(processInstanceEvent);

        assertThat(processInstanceEvent)
                .isCompleted()
                .hasPassedElement("InvokeBooleanRandomizer_ServiceTask")
                .hasPassedElement("InvokeHappiness_ServiceTask")
                .hasVariableWithValue("Happy", true);
        Mockito.verify(exampleService, times(1)).trueFalseExample();
    }
}
