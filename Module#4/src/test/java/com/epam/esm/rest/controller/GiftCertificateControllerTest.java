package com.epam.esm.rest.controller;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.exception.service.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Gift certificate controller test.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GiftCertificateControllerTest {
    private static final String GET_GIFT_CERTIFICATE_BY_ID_URI = "/api/v1/giftCertificates/1";
    private static final String GET_GIFT_CERTIFICATES_BY_TAG_NAMES_URI = "/api/v1/giftCertificates/tags";

    @Autowired
    private WebApplicationContext context;
    /**
     * The Gift certificate service.
     */
    @MockBean
    GiftCertificateService giftCertificateService;

    private MockMvc mvc;

    /**
     * Sets .
     */
    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    /**
     * Gets gift certificate test without role should return status ok.
     *
     * @throws Exception the exception
     */
    @Test
    void getGiftCertificateTest_WITHOUT_ROLE_SHOULD_RETURN_STATUS_OK() throws Exception {
        when(giftCertificateService.getById(anyLong()))
                .thenReturn(Mockito.spy(GiftCertificate.class));

        mvc.perform(get(GET_GIFT_CERTIFICATE_BY_ID_URI)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Gets list gift certificates by tag names test without role should return status ok.
     *
     * @throws Exception the exception
     */
    @Test
    void getListGiftCertificatesByTagNamesTest_WITHOUT_ROLE_SHOULD_RETURN_STATUS_OK() throws Exception {
        String existingTagName = "";
        when(giftCertificateService.getListByTagNames(anyList(), any(Pageable.class)))
                .thenReturn(Page.empty());

        mvc.perform(get(GET_GIFT_CERTIFICATES_BY_TAG_NAMES_URI)
                .param("name", existingTagName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Gets list gift certificates by tag names test without role should return status not found.
     *
     * @throws Exception the exception
     */
    @Test
    void getListGiftCertificatesByTagNamesTest_WITHOUT_ROLE_SHOULD_RETURN_STATUS_NOT_FOUND() throws Exception {
        String notExistingTagName = "";
        when(giftCertificateService.getListByTagNames(anyList(), any(Pageable.class)))
                .thenThrow(new ResourceNotFoundException());

        mvc.perform(get(GET_GIFT_CERTIFICATES_BY_TAG_NAMES_URI)
                .param("name", notExistingTagName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
