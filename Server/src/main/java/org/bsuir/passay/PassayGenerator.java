package org.bsuir.passay;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Service;

@Service
public class PassayGenerator implements PasswordGeneratorService {

    @Override
    public String generatePassword(int passwordLength) {
        CharacterRule digits = new CharacterRule(EnglishCharacterData.Digit);
        CharacterRule alphabetical = new CharacterRule(EnglishCharacterData.Alphabetical);

        PasswordGenerator passwordGenerator = new PasswordGenerator();
        return passwordGenerator.generatePassword(6, digits,alphabetical);
    }
}
