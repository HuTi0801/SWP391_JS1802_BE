package com.js1802_team5.diamondShop.service_implementors;

import com.js1802_team5.diamondShop.models.entity_models.DiamondShell;
import com.js1802_team5.diamondShop.models.entity_models.Size;
import com.js1802_team5.diamondShop.models.entity_models.SizeDiamondShell;
import com.js1802_team5.diamondShop.repositories.DiamondShellRepo;
import com.js1802_team5.diamondShop.repositories.SizeDiamondShellRepo;
import com.js1802_team5.diamondShop.repositories.SizeRepo;
import com.js1802_team5.diamondShop.services.DiamondShellService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiamondShellServiceImpl implements DiamondShellService {
    @Autowired
    private DiamondShellRepo diamondShellRepo;
    @Autowired
    private SizeRepo sizeRepo;
    @Autowired
    private SizeDiamondShellRepo sizeDiamondShellRepo;

    @Override
    public DiamondShell createDiamondShell(DiamondShell diamondShell) {
        return diamondShellRepo.save(diamondShell);
    }

    @Override
    public List<DiamondShell> getAllDiamondShell() {
        return diamondShellRepo.findAll();
    }

    //add Size to Diamond Shell
    @Override
    public DiamondShell addSizeToDiamondShell(Integer diamondShellId, Integer sizeId) {
        DiamondShell diamondShell = diamondShellRepo.findById(diamondShellId).orElseThrow();
        Size size = sizeRepo.findById(sizeId).orElseThrow();

        SizeDiamondShell sizeDiamondShell = new SizeDiamondShell();
        sizeDiamondShell.setDiamondShell(diamondShell);
        sizeDiamondShell.setSize(size);

        sizeDiamondShellRepo.save(sizeDiamondShell);
        diamondShell.getSizeDiamondShellList().add(sizeDiamondShell);
        return diamondShellRepo.save(diamondShell);
    }

    //get a diamond shell
    @Override
    public DiamondShell getADiamondShell(Integer id) {
        Optional<DiamondShell> diamondShell = diamondShellRepo.findById(id);
        if (diamondShell.isPresent()) {
            return diamondShell.get();
        } else {
            throw new RuntimeException("Diamond Shell not found with id " + id);
        }
    }
}
