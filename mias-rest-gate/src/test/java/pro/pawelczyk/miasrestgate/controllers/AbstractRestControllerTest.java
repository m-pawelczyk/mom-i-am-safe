package pro.pawelczyk.miasrestgate.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * m-pawelczyk (GitGub) / m_pawelczyk (Twitter)
 * on 16.06.2020
 * created AbstractRestControllerTest in pro.pawelczyk.miasrestgate.controllers
 * in project mias-rest-gate
 */
public abstract class AbstractRestControllerTest {

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

