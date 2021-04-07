package com.gmail.sergick6690;

import com.gmail.sergick6690.implementation.JdbcAudienceDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.Mockito.doNothing;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
////@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = SpringConfig.class)
class TablesCreatorTest {


    @Autowired
    private JdbcAudienceDAO audienceDAO;
    @Mock
    private TablesCreator creator;


    @Test
    void createTables() throws IOException, URISyntaxException {
        doNothing().when(creator).createTables("Script.sql");
    }
}