package com.clinic.system.domain.consultation.valid;

import com.clinic.system.domain.consultation.ConsultationInputDto;
import static org.junit.Assert.*;

import com.clinic.system.infra.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
public class ValidateClinicOpeningHoursTest {
    ValidateClinicOpeningHours validateClinicOpeningHours = new ValidateClinicOpeningHours();
    @Test
    @DisplayName("Should Return Exception When ConsultationDate Is Saturday")
    public void scene_1(){
        var inputDto = new ConsultationInputDto( 1L,1L, LocalDateTime.of(2023,5,13,10,0));

        var ex = assertThrows(CustomException.class, () -> validateClinicOpeningHours.validate(inputDto));
        assertEquals("Appointment outside clinic opening hours. \n" +
                "time sent: " + inputDto.getConsultationDateTime(),ex.getMessage());
    }
    @Test
    @DisplayName("should Return Exception When ConsultationDate Is Sunday")
    public void scene_2(){
        var inputDto = new ConsultationInputDto( 1L,1L, LocalDateTime.of(2023,5,14,10,0));

        var ex = assertThrows(CustomException.class, () -> validateClinicOpeningHours.validate(inputDto));
        assertEquals("Appointment outside clinic opening hours. \n" +
                "time sent: " + inputDto.getConsultationDateTime(),ex.getMessage());
    }
    @Test
    @DisplayName("Should Return Exception When ConsultationTime Is Before 7 oClock")
    public void scene_3(){
        var inputDto = new ConsultationInputDto( 1L,1L, LocalDateTime.of(2023,5,12,6,55));

        var ex = assertThrows(CustomException.class, () -> validateClinicOpeningHours.validate(inputDto));
        assertEquals("Appointment outside clinic opening hours. \n" +
                "time sent: " + inputDto.getConsultationDateTime(),ex.getMessage());
    }
    @Test
    @DisplayName("should Return Code 0 When ConsultationTime Is Exactly 7 oClock")
    public void scene_4(){
        var inputDto = new ConsultationInputDto( 1L,1L, LocalDateTime.of(2023,5,12,7,0));
        assertEquals(0,validateClinicOpeningHours.validate(inputDto));
    }
    @Test
    @DisplayName("Should Return Exception When ConsultationTime Is After 18 oClock")
    public void scene_5(){
        var inputDto = new ConsultationInputDto( 1L,1L, LocalDateTime.of(2023,5,12,18,1));
        var ex = assertThrows(CustomException.class, () -> validateClinicOpeningHours.validate(inputDto));
        assertEquals("Appointment outside clinic opening hours. \n" +
                "time sent: " + inputDto.getConsultationDateTime(),ex.getMessage());
    }
    @Test
    @DisplayName("Should Return Code 0 When ConsultationTime Is Exactly 18 oClock")
    public void scene_6(){
        var inputDto = new ConsultationInputDto( 1L,1L, LocalDateTime.of(2023,5,12,18,0));
        assertEquals(0,validateClinicOpeningHours.validate(inputDto));
    }

}
