package com.dtyao.crowdfunding.service;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ProjectService {
    /**
     * Calculate deadline
     *
     * @param creatAt date of creation
     * @param days duration days
     * @return deadline
     */
    public LocalDate calculateDdl(LocalDate creatAt, int days){
        LocalDate ddl = creatAt.plusDays(days);
        return ddl;
    }
}
