package springboot.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.springframework.stereotype.Service;
import springboot.RoomDto;

@Service
public class AddRoomService {

    public void addRoom(RoomDto roomDto) throws IOException {
        try (FileWriter fw = new FileWriter("spring-boot/src/main/resources/Rooms.csv", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(roomDto.getRoomNumber() + "," + roomDto.getCost() + "," + roomDto.getRoomType() + ","
                    + roomDto.getNumOfBeds() + "," + roomDto.getQualityLevel() + "," + roomDto.getBedType() + ","
                    + roomDto.getSmokingAllowed());
        } catch (IOException e) {
            throw new IOException("Failed to add room to CSV", e);
        }
    }
}
