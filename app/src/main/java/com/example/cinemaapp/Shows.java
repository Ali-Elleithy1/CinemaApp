
package com.example.cinemaapp;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.*;
import com.backendless.geo.GeoPoint;

import java.util.List;
import java.util.Date;

public class Shows
{
  private String type;
  private String seats;
  private String movieName;
  private Date updated;
  private String showTime;
  private String ownerId;
  private Date created;
  private String objectId;
  public String getType()
  {
    return type;
  }

  public void setType( String type )
  {
    this.type = type;
  }

  public String getSeats()
  {
    return seats;
  }

  public void setSeats( String seats )
  {
    this.seats = seats;
  }

  public String getMovieName()
  {
    return movieName;
  }

  public void setMovieName( String movieName )
  {
    this.movieName = movieName;
  }

  public Date getUpdated()
  {
    return updated;
  }

  public String getShowTime()
  {
    return showTime;
  }

  public void setShowTime( String showTime )
  {
    this.showTime = showTime;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public Date getCreated()
  {
    return created;
  }

  public String getObjectId()
  {
    return objectId;
  }

                                                    
  public Shows save()
  {
    return Backendless.Data.of( Shows.class ).save( this );
  }

  public void saveAsync( AsyncCallback<Shows> callback )
  {
    Backendless.Data.of( Shows.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( Shows.class ).remove( this );
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( Shows.class ).remove( this, callback );
  }

  public static Shows findById( String id )
  {
    return Backendless.Data.of( Shows.class ).findById( id );
  }

  public static void findByIdAsync( String id, AsyncCallback<Shows> callback )
  {
    Backendless.Data.of( Shows.class ).findById( id, callback );
  }

  public static Shows findFirst()
  {
    return Backendless.Data.of( Shows.class ).findFirst();
  }

  public static void findFirstAsync( AsyncCallback<Shows> callback )
  {
    Backendless.Data.of( Shows.class ).findFirst( callback );
  }

  public static Shows findLast()
  {
    return Backendless.Data.of( Shows.class ).findLast();
  }

  public static void findLastAsync( AsyncCallback<Shows> callback )
  {
    Backendless.Data.of( Shows.class ).findLast( callback );
  }

  public static List<Shows> find( DataQueryBuilder queryBuilder )
  {
    return Backendless.Data.of( Shows.class ).find( queryBuilder );
  }

  public static void findAsync( DataQueryBuilder queryBuilder, AsyncCallback<List<Shows>> callback )
  {
    Backendless.Data.of( Shows.class ).find( queryBuilder, callback );
  }
}