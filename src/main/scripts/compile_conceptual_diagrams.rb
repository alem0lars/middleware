#!/usr/bin/env ruby

Dir.glob("#{ENV['SRC_DIR']}/**/*.odg") do |src_pth|
  puts `libreoffice --headless --convert-to svg --outdir #{ENV['OUT_DIR']} #{src_pth}`
end
