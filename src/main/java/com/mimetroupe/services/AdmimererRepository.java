package com.mimetroupe.services;

import com.mimetroupe.entities.Admimerer;
import com.mimetroupe.entities.Mime;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by branden on 3/17/16 at 14:17.
 *
 * This repository deals with the Admimerer table which connects mimes to their mime matches
 * methods in here will return custom DB queries
 */
public interface AdmimererRepository extends CrudRepository<Admimerer, Integer> {


    //SELECT admimerer_ID FROM admimerer WHERE mime_id = ?
    //returns all mimes that a specific mime admimers.
    List<Admimerer> findByMime(Mime mime);

    //returns all mimes that admimerer a specific mine. IE all mimes that like a user mime.
    List<Admimerer> findByAdmimerer(Mime mime);


    //SELECT admimerer_Id FROM admimerer WHERE admimerer_id = mime_id  ????? THIS ISNT RIGHT
    List<Admimerer> findMimeByMimeEquals(Mime mime);  //????? EEGGH? what is this. Is this going to do something?!

    //    @Query(value = "DELETE FROM admimerer WHERE mime_id = ?1 AND admimerer_id = ?1"  ,nativeQuery = true)//    Set<Mime> deleteCascade(int id);


}
