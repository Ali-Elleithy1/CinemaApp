
package com.example.cinemaapp;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.*;
import com.backendless.geo.GeoPoint;

import java.io.Serializable;
import java.util.List;
import java.util.Date;

public class Movies implements Serializable {
  private Date created;
  private String subtitles;
  private String plot;
  private String rate;
  private String name;
  private String trailer;
  private String language;
  private String poster;
  private Integer duration;
  private String genre;
  private String ownerId;
  private Date updated;
  private Date rdate;
  private String objectId;
  public Date getCreated()
  {
    return created;
  }

  public String getSubtitles()
  {
    return subtitles;
  }

  public void setSubtitles( String subtitles )
  {
    this.subtitles = subtitles;
  }

  public String getPlot()
  {
    return plot;
  }

  public void setPlot( String plot )
  {
    this.plot = plot;
  }

  public String getRate()
  {
    return rate;
  }

  public void setRate( String rate )
  {
    this.rate = rate;
  }

  public String getName()
  {
    return name;
  }

  public void setName( String name )
  {
    this.name = name;
  }

  public String getTrailer()
  {
    return trailer;
  }

  public void setTrailer( String trailer )
  {
    this.trailer = trailer;
  }

  public String getLanguage()
  {
    return language;
  }

  public void setLanguage( String language )
  {
    this.language = language;
  }

  public String getPoster()
  {
    return poster;
  }

  public void setPoster( String poster )
  {
    this.poster = poster;
  }

  public Integer getDuration()
  {
    return duration;
  }

  public void setDuration( Integer duration )
  {
    this.duration = duration;
  }

  public String getGenre()
  {
    return genre;
  }

  public void setGenre( String genre )
  {
    this.genre = genre;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public Date getUpdated()
  {
    return updated;
  }

  public Date getRdate()
  {
    return rdate;
  }

  public void setRdate( Date rdate )
  {
    this.rdate = rdate;
  }

  public String getObjectId()
  {
    return objectId;
  }

                                                    
  public Movies save()
  {
    return Backendless.Data.of( Movies.class ).save( this );
  }

  public void saveAsync( AsyncCallback<Movies> callback )
  {
    Backendless.Data.of( Movies.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( Movies.class ).remove( this );
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( Movies.class ).remove( this, callback );
  }

  public static Movies findById( String id )
  {
    return Backendless.Data.of( Movies.class ).findById( id );
  }

  public static void findByIdAsync( String id, AsyncCallback<Movies> callback )
  {
    Backendless.Data.of( Movies.class ).findById( id, callback );
  }

  public static Movies findFirst()
  {
    return Backendless.Data.of( Movies.class ).findFirst();
  }

  public static void findFirstAsync( AsyncCallback<Movies> callback )
  {
    Backendless.Data.of( Movies.class ).findFirst( callback );
  }

  public static Movies findLast()
  {
    return Backendless.Data.of( Movies.class ).findLast();
  }

  public static void findLastAsync( AsyncCallback<Movies> callback )
  {
    Backendless.Data.of( Movies.class ).findLast( callback );
  }

  public static List<Movies> find( DataQueryBuilder queryBuilder )
  {
    return Backendless.Data.of( Movies.class ).find( queryBuilder );
  }

  public static void findAsync( DataQueryBuilder queryBuilder, AsyncCallback<List<Movies>> callback )
  {
    Backendless.Data.of( Movies.class ).find( queryBuilder, callback );
  }
}