package base64.base64.Entity;



import lombok.Data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Data
@Document(collection="Password")
public class Entity {


@Id
private ObjectId ID;


@Indexed(unique = true)
@NonNull
private String username;

@NonNull
private String password;

    
}
