
package com.example.cinemaapp;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.persistence.*;
import com.backendless.geo.GeoPoint;

import java.util.List;
import java.util.Date;

public class Transactions
{
  private Date updated;
  private String type;
  private String showTime;
  private String name;
  private Date created;
  private Integer quantity;
  private String movieName;
  private String seats;
  private Integer total;
  private String ownerId;
  private String objectId;
  public Date getUpdated()
  {
    return updated;
  }

  public String getType()
  {
    return type;
  }

  public void setType( String type )
  {
    this.type = type;
  }

  public String getShowTime()
  {
    return showTime;
  }

  public void setShowTime( String showTime )
  {
    this.showTime = showTime;
  }

  public String getName()
  {
    return name;
  }

  public void setName( String name )
  {
    this.name = name;
  }

  public Date getCreated()
  {
    return created;
  }

  public Integer getQuantity()
  {
    return quantity;
  }

  public void setQuantity( Integer quantity )
  {
    this.quantity = quantity;
  }

  public String getMovieName()
  {
    return movieName;
  }

  public void setMovieName( String movieName )
  {
    this.movieName = movieName;
  }

  public String getSeats()
  {
    return seats;
  }

  public void setSeats( String seats )
  {
    this.seats = seats;
  }

  public Integer getTotal()
  {
    return total;
  }

  public void setTotal( Integer total )
  {
    this.total = total;
  }

  public String getOwnerId()
  {
    return ownerId;
  }

  public String getObjectId()
  {
    return objectId;
  }

                                                    
  public Transactions save()
  {
    return Backendless.Data.of( Transactions.class ).save( this );
  }

  public void saveAsync( AsyncCallback<Transactions> callback )
  {
    Backendless.Data.of( Transactions.class ).save( this, callback );
  }

  public Long remove()
  {
    return Backendless.Data.of( Transactions.class ).remove( this );
  }

  public void removeAsync( AsyncCallback<Long> callback )
  {
    Backendless.Data.of( Transactions.class ).remove( this, callback );
  }

  public static Transactions findById( String id )
  {
    return Backendless.Data.of( Transactions.class ).findById( id );
  }

  public static void findByIdAsync( String id, AsyncCallback<Transactions> callback )
  {
    Backendless.Data.of( Transactions.class ).findById( id, callback );
  }

  public static Transactions findFirst()
  {
    return Backendless.Data.of( Transactions.class ).findFirst();
  }

  public static void findFirstAsync( AsyncCallback<Transactions> callback )
  {
    Backendless.Data.of( Transactions.class ).findFirst( callback );
  }

  public static Transactions findLast()
  {
    return Backendless.Data.of( Transactions.class ).findLast();
  }

  public static void findLastAsync( AsyncCallback<Transactions> callback )
  {
    Backendless.Data.of( Transactions.class ).findLast( callback );
  }

  public static List<Transactions> find( DataQueryBuilder queryBuilder )
  {
    return Backendless.Data.of( Transactions.class ).find( queryBuilder );
  }

  public static void findAsync( DataQueryBuilder queryBuilder, AsyncCallback<List<Transactions>> callback )
  {
    Backendless.Data.of( Transactions.class ).find( queryBuilder, callback );
  }
}