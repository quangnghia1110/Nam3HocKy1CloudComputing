package hcmute.serviceImpl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcmute.model.order.OrderTrack;
import hcmute.repository.OrderTrackRepository;
import hcmute.service.IOrderTrackService;

import java.util.List;
@Service
@Slf4j
public class OrderTrackServiceImpl implements IOrderTrackService {
    @Autowired
    OrderTrackRepository orderTrackRepository;
    @Override
    public List<OrderTrack> getAllOrderTrack() {
        return orderTrackRepository.findAll();
    }
}
