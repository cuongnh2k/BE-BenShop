package click.benshop.bebenshop.bases;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    public ResponseEntity<BaseResponseDto> success(String message) {
        return ResponseEntity.ok(BaseResponseDto.success(message));
    }

    public ResponseEntity<BaseResponseDto> success(Object data, String message) {
        return ResponseEntity.ok(BaseResponseDto.success(data, message));
    }

    public ResponseEntity<BaseResponseDto> created(Object data, String message) {
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseDto.success(data, message));
    }
}