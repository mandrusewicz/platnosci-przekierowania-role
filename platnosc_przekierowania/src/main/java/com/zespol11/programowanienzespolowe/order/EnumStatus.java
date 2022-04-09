package com.zespol11.programowanienzespolowe.order;

public enum EnumStatus {
    ORDERED{
        @Override
        public boolean isOrdered(){return true;}
    },
    PREPARING{
        @Override
        public boolean isPreparing(){return true;}
    },
    READY{
        @Override
        public boolean isReady(){return true;}
    },
    DELIVERED{
        @Override
        public boolean isDelivered(){return true;}
    };


    public boolean isOrdered(){return false;}

    public boolean isPreparing(){return false;}

    public boolean isReady(){return false;}

    public boolean isDelivered(){return false;}

}
