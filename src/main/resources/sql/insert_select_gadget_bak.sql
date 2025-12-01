insert into gadget_bak ( gid , model ,  brand ,  price ,  amount)
    select  gid , model ,  brand ,  price ,  [AMOUNT]
        from gadget where gid = {GID};