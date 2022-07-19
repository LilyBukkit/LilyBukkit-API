package org.bukkit.configuration.file;

import org.bukkit.configuration.*;

/**
 * Various settings for controlling the input and output of a {@link FileConfiguration}
 */
public class FileConfigurationOptions extends MemoryConfigurationOptions {
    private String header = null;
    
    protected FileConfigurationOptions(MemoryConfiguration configuration) {
        super(configuration);
    }

    @Override
    public FileConfiguration configuration() {
        return (FileConfiguration)super.configuration();
    }

    @Override
    public FileConfigurationOptions copyDefaults(boolean value) {
        super.copyDefaults(value);
        return this;
    }

    @Override
    public FileConfigurationOptions pathSeparator(char value) {
        super.pathSeparator(value);
        return this;
    }
    
    /**
     * Gets the header that will be applied to the top of the saved output.
     * <p>
     * This header will be commented out and applied directly at the top of the
     * generated output of the {@link FileConfiguration}. It is not required to
     * include a newline at the end of the header as it will automatically be applied,
     * but you may include one if you wish for extra spacing.
     * <p>
     * Null is a valid value which will indicate that no header is to be applied.
     * The default value is null.
     * 
     * @return Header
     */
    public String header() {
        return header;
    }
    
    /**
     * Sets the header that will be applied to the top of the saved output.
     * <p>
     * This header will be commented out and applied directly at the top of the
     * generated output of the {@link FileConfiguration}. It is not required to
     * include a newline at the end of the header as it will automatically be applied,
     * but you may include one if you wish for extra spacing.
     * <p>
     * Null is a valid value which will indicate that no header is to be applied.
     * The default value is null.
     * 
     * @param value New header
     * @return This object, for chaining
     */
    public FileConfigurationOptions header(String value) {
        this.header = value;
        return this;
    }
}
