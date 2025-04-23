
package com.digis01.MMarinProgrmacionNCapasSpring.DAO;

import com.digis01.MMarinProgrmacionNCapasSpring.ML.Result;


public interface IColoniaDAO {
    
    Result ColoniaBydIdMunicipio(int IdMunicipio);
    
    Result ColoniaByCodigoPostal(String CodigoPostal);
}
