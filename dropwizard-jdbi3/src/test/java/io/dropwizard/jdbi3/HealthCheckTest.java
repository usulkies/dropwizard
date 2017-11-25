package io.dropwizard.jdbi3;

import com.codahale.metrics.health.HealthCheck.Result;
import com.google.common.util.concurrent.MoreExecutors;
import io.dropwizard.util.Duration;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.MappingException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HealthCheckTest {

    private JdbiHealthCheck healthCheck;

    private Jdbi jdbi = mock(Jdbi.class);
    private Handle handle = mock(Handle.class);

    @Before
    public void init() {
        when(jdbi.open()).thenReturn(handle);
        healthCheck = new JdbiHealthCheck(MoreExecutors.newDirectExecutorService(), Duration.seconds(5), jdbi,
            "select 1");
    }

    @Test
    public void shouldReturnNotHealthyBecauseOfErrorOnError() throws Exception {
        when(handle.execute("select 1")).thenThrow(new MappingException("bad error here"));

        Result check = healthCheck.check();
        assertThat(check).isNotNull()
            .extracting(Result::getMessage)
            .containsOnly("bad error here");
    }
}
