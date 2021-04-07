package com.gmail.sergick6690;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.Mockito.doNothing;

@MockitoSettings(strictness = Strictness.LENIENT)
class TablesCreatorTest {

    @Mock
    private TablesCreator creator;

    @Test
    void createTables() throws IOException, URISyntaxException {
        doNothing().when(creator).createTables("Script.sql");
    }
}