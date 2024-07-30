/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package course.examples.Services.KeyCommon;
// Define the methods shared between both server and client

public interface KeyGenerator extends android.os.IInterface
{
  /** Default implementation for KeyGenerator. */
  public static class Default implements course.examples.Services.KeyCommon.KeyGenerator
  {
    @Override public void playClip(java.lang.String clipName) throws android.os.RemoteException
    {
    }
    @Override public void pauseClip() throws android.os.RemoteException
    {
    }
    @Override public void resumeClip() throws android.os.RemoteException
    {
    }
    @Override public void stopClip() throws android.os.RemoteException
    {
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements course.examples.Services.KeyCommon.KeyGenerator
  {
    private static final java.lang.String DESCRIPTOR = "course.examples.Services.KeyCommon.KeyGenerator";
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an course.examples.Services.KeyCommon.KeyGenerator interface,
     * generating a proxy if needed.
     */
    public static course.examples.Services.KeyCommon.KeyGenerator asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof course.examples.Services.KeyCommon.KeyGenerator))) {
        return ((course.examples.Services.KeyCommon.KeyGenerator)iin);
      }
      return new course.examples.Services.KeyCommon.KeyGenerator.Stub.Proxy(obj);
    }
    @Override public android.os.IBinder asBinder()
    {
      return this;
    }
    @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
    {
      java.lang.String descriptor = DESCRIPTOR;
      switch (code)
      {
        case INTERFACE_TRANSACTION:
        {
          reply.writeString(descriptor);
          return true;
        }
        case TRANSACTION_playClip:
        {
          data.enforceInterface(descriptor);
          java.lang.String _arg0;
          _arg0 = data.readString();
          this.playClip(_arg0);
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_pauseClip:
        {
          data.enforceInterface(descriptor);
          this.pauseClip();
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_resumeClip:
        {
          data.enforceInterface(descriptor);
          this.resumeClip();
          reply.writeNoException();
          return true;
        }
        case TRANSACTION_stopClip:
        {
          data.enforceInterface(descriptor);
          this.stopClip();
          reply.writeNoException();
          return true;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
    }
    private static class Proxy implements course.examples.Services.KeyCommon.KeyGenerator
    {
      private android.os.IBinder mRemote;
      Proxy(android.os.IBinder remote)
      {
        mRemote = remote;
      }
      @Override public android.os.IBinder asBinder()
      {
        return mRemote;
      }
      public java.lang.String getInterfaceDescriptor()
      {
        return DESCRIPTOR;
      }
      @Override public void playClip(java.lang.String clipName) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(clipName);
          boolean _status = mRemote.transact(Stub.TRANSACTION_playClip, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().playClip(clipName);
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void pauseClip() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_pauseClip, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().pauseClip();
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void resumeClip() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_resumeClip, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().resumeClip();
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void stopClip() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_stopClip, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            getDefaultImpl().stopClip();
            return;
          }
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      public static course.examples.Services.KeyCommon.KeyGenerator sDefaultImpl;
    }
    static final int TRANSACTION_playClip = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_pauseClip = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_resumeClip = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
    static final int TRANSACTION_stopClip = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
    public static boolean setDefaultImpl(course.examples.Services.KeyCommon.KeyGenerator impl) {
      // Only one user of this interface can use this function
      // at a time. This is a heuristic to detect if two different
      // users in the same process use this function.
      if (Stub.Proxy.sDefaultImpl != null) {
        throw new IllegalStateException("setDefaultImpl() called twice");
      }
      if (impl != null) {
        Stub.Proxy.sDefaultImpl = impl;
        return true;
      }
      return false;
    }
    public static course.examples.Services.KeyCommon.KeyGenerator getDefaultImpl() {
      return Stub.Proxy.sDefaultImpl;
    }
  }
  public void playClip(java.lang.String clipName) throws android.os.RemoteException;
  public void pauseClip() throws android.os.RemoteException;
  public void resumeClip() throws android.os.RemoteException;
  public void stopClip() throws android.os.RemoteException;
}
