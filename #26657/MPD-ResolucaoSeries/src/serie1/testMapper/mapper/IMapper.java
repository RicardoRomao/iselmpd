package serie1.testMapper.mapper;

public interface IMapper<TSrc, TDest> {

    public TDest map(TSrc srcObj);

}
