/**
 * Copyright (C) 2017 Czech Technical University in Prague
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package cz.cvut.kbss.reporting.environment.config;

import cz.cvut.kbss.reporting.service.OccurrenceReportService;
import cz.cvut.kbss.reporting.service.data.export.ReportExporter;
import cz.cvut.kbss.reporting.service.jmx.AppAdminBean;
import cz.cvut.kbss.reporting.service.repository.RepositoryOccurrenceReportService;
import cz.cvut.kbss.reporting.service.security.LoginTracker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@Configuration
@ComponentScan(basePackages = "cz.cvut.kbss.reporting.service")
public class TestServiceConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public OccurrenceReportService occurrenceReportService() {
        return spy(new RepositoryOccurrenceReportService());
    }

    @Bean
    public ReportExporter reportExporter() {
        return mock(ReportExporter.class);
    }

    @Bean
    public LoginTracker loginTracker() {
        return mock(LoginTracker.class);
    }

    @Bean
    public AppAdminBean appAdminBean() {
        return mock(AppAdminBean.class);
    }
}
